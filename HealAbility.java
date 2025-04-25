public class HealAbility extends Ability{
    private Player player;
    private int charges;
    private int maxCharges;
    public HealAbility(Player player) {
        super("Heal", "heal_icon", 0);
        this.player = player;
        this.charges = 1;
        this.maxCharges = 5;
    }

    @Override
    public void activate() {
       if(charges > 0) {
            player.heal();
            charges --;
            isAvailable = charges > 0;
            //SoundManager.getInstance().playClip("heal", false);
        }
    }

    public void addCharge() {
        if(charges < maxCharges) {
            charges ++; 
            isAvailable = true;
        }
    }

    public int getCharges() {
        return charges;
    }

    public int getMaxCharges() {
        return maxCharges;
    }
    
}
