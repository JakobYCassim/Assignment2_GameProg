public abstract class Ability {
    protected boolean isAvailable;
    protected int cooldown;
    protected int maxCooldown;
    protected String name;
    protected String iconName;

    public Ability(String name, String iconName, int maxCooldown) {
        this.name = name;
        this.iconName = iconName;
        this.maxCooldown = maxCooldown;
        this.cooldown = 0;
        this.isAvailable = true;
    }

    public abstract void activate();

    public void update() {
        if(!isAvailable && cooldown > 0) {
            cooldown --;
            if(cooldown <= 0) {
                isAvailable = true;
            }
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getMaxCooldown() {
        return maxCooldown;
    }

    public String getName() {
        return name;
    }

    public String getIconName() {
        return iconName;
    }

}
