package io.github.necrashter.natural_revenge.world.player;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import io.github.necrashter.natural_revenge.world.GameWorld;

public class NoRecoilWeapon extends PlayerWeapon {
    private int currentAmmo = 250;
    private int maxAmmo = 250;
    private boolean isReloading = false;
    private float reloadTimer = 0f;
    private final float reloadTime = 2.0f; // 2 second reload time
    
    // Weapon stats - all recoil values set to 0
    private final float recoilAmount = 0f;
    private final float recoilRecovery = 0f;
    private final float spread = 0f;
    
    public NoRecoilWeapon(Player player) {
        super(player);
        this.speedMod = 1.0f; // Normal movement speed
        // Initialize viewModel here if you have a weapon model
        // this.viewModel = new ModelInstance(yourWeaponModel);
    }

    @Override
    public void update(float delta) {
        if (isReloading) {
            reloadTimer += delta;
            if (reloadTimer >= reloadTime) {
                isReloading = false;
                reloadTimer = 0f;
                currentAmmo = maxAmmo; // Full ammo reload
            }
        }
        
        // No recoil logic needed since all recoil values are 0
        // The weapon will maintain perfect accuracy with no camera shake
    }

    @Override
    public void render(GameWorld world) {
        if (viewModel != null) {
            world.modelBatch.render(viewModel, world.environment);
        }
    }

    @Override
    public void buildText(StringBuilder stringBuilder) {
        stringBuilder.append("WEAPON: NO RECOIL MODE\n");
        stringBuilder.append("AMMO: ").append(currentAmmo).append("/").append(maxAmmo).append("\n");
        
        if (isReloading) {
            float progress = (reloadTimer / reloadTime) * 100f;
            stringBuilder.append("RELOADING: ").append(String.format("%.1f", progress)).append("%\n");
        } else {
            stringBuilder.append("READY TO FIRE\n");
        }
        
        stringBuilder.append("SPREAD: 0.0° | RECOIL: NONE");
    }

    @Override
    public void onEquip() {
        // Reset any states when weapon is equipped
        isReloading = false;
        reloadTimer = 0f;
        
        // No need to reset recoil since there is none
        // Camera will remain perfectly stable
    }

    // Additional methods for weapon functionality
    public void fire() {
        if (!isReloading && currentAmmo > 0) {
            currentAmmo--;
            
            // No recoil applied to player camera
            // No spread calculation needed since spread is 0
            
            if (currentAmmo <= 0) {
                startReload();
            }
        }
    }

    public void startReload() {
        if (!isReloading && currentAmmo < maxAmmo) {
            isReloading = true;
            reloadTimer = 0f;
        }
    }

    public void cancelReload() {
        isReloading = false;
        reloadTimer = 0f;
    }

    // Getters for weapon state
    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public boolean canFire() {
        return !isReloading && currentAmmo > 0;
    }

    // Method to add ammo (for pickups)
    public void addAmmo(int amount) {
        if (!isReloading) {
            currentAmmo = Math.min(currentAmmo + amount, maxAmmo);
        }
    }

    @Override
    public void setView(Camera camera) {
        super.setView(camera);
        // Additional view adjustments can be made here if needed
        // No recoil offset applied
    }
}