package visibles.players;


import static settings.GameConstants.PhysicalParameters.GRAVITY;
import static settings.GameConstants.PlayerParameters.BOUNCE_ELASTICITY_COEF;
import static settings.GameConstants.PlayerParameters.IN_AIR_JUMP_POWER;
import static settings.GameConstants.PlayerParameters.JUMP_POWER;
import static settings.GameConstants.PlayerParameters.RUN_SPEED;
import static settings.GameConstants.PlayerParameters.TURN_SPEED;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import core.ifaces.ITerrainable;
import core.utils.Maths;
import entities.Entity;

/**
 * @author storm
 *
 */
public class PlayerEntity
        extends Entity<PlayerTexturedModel> {

    private float currentRunSpeed     = 0;
    private float currentUpwardsSpeed = 0;
    private float currentTurnSpeed    = 0;

    private boolean inAir = false;

    private boolean extremeFOVEnabled = true;


    public boolean extremeFOVEnabledQ() {
        return extremeFOVEnabled;
    }


    public PlayerEntity(PlayerTexturedModel texturedModel, Vector3f position, float scale) {
        super(texturedModel, position, scale);
    }


    public PlayerEntity(PlayerTexturedModel texturedModel, Vector3f position) {
        super(texturedModel, position);
    }


    public void move(ITerrainable<?, ?> terrain) {
        checkInputs();
        final float secsSinceLastFrame = worldTimeDeltaInSecs();

        final float angularVelocity = calcAngularVelocity(secsSinceLastFrame);
        increaseRotation(0, angularVelocity, 0);

        final float distance = currentRunSpeed * secsSinceLastFrame;
        verticallyAccelerate(GRAVITY, secsSinceLastFrame);

        final float dx = distance * Maths.sinFromDegs(getRotY());
        final float dz = distance * Maths.cosFromDegs(getRotY());
        final float dy = calcPositionComponentY(secsSinceLastFrame);
        increasePosition(dx, dy, dz);

        checkTerrainCollision(terrain);
    }


    private void verticallyAccelerate(float forceInNewtons, float secs) {
        currentUpwardsSpeed += forceInNewtons * secs;
    }


    private float calcPositionComponentY(float secs) {
        return currentUpwardsSpeed * secs;
    }


    private float calcAngularVelocity(float secs) {
        return currentTurnSpeed * secs;
    }


    private void bounce() {
        currentUpwardsSpeed *= BOUNCE_ELASTICITY_COEF;
    }


    private void checkTerrainCollision(ITerrainable<?, ?> terrain) {
        final float terrainHeight = getHeightOfTerrain(terrain);

        if (atOrBelowHeightQ(terrainHeight)) {
            getPosition().y = terrainHeight;

            final float thresh = 10f;
            if (Math.abs(currentUpwardsSpeed) >= thresh) {
                bounce();
                inAir = true;
            } else {
                currentUpwardsSpeed = 0;
                inAir = false;
            }
        }
    }


    protected float getHeightOfTerrain(ITerrainable<?, ?> terrain) {
        return terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
    }


    private boolean atOrBelowHeightQ(float height) {
        return getPosition().y <= height;
    }


    private void jump() {
        if (!inAir)
            this.currentUpwardsSpeed += JUMP_POWER;
        else
            this.currentUpwardsSpeed += IN_AIR_JUMP_POWER;
        inAir = true;
    }


    protected void checkInputs() {

        // move forward/backward
        final boolean up = Keyboard.isKeyDown(Keyboard.KEY_W);
        final boolean down = Keyboard.isKeyDown(Keyboard.KEY_S);

        if (up && !down) {
            this.currentRunSpeed = RUN_SPEED;
        } else if (down && !up) {
            this.currentRunSpeed = -RUN_SPEED;
        } else {
            this.currentRunSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            this.extremeFOVEnabled = true;
        } else {
            this.extremeFOVEnabled = false;
        }

        // // strafe forward/backward
        // if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
        // this.currentRunSpeed = RUN_SPEED;
        // } else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
        // this.currentRunSpeed = -RUN_SPEED;
        // } else {
        // this.currentRunSpeed = 0;
        // }

        // turn left/right
        final boolean left = Keyboard.isKeyDown(Keyboard.KEY_A);
        final boolean right = Keyboard.isKeyDown(Keyboard.KEY_D);
        if (left && !right) {
            this.currentTurnSpeed = TURN_SPEED;
        } else if (right && !left) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        final boolean jump = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        if (jump) {
            jump();
        }
    }


    public boolean isInAir() {
        return inAir;
    }

}
