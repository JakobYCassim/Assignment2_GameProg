public class HealAbility extends Ability{
    private Player player;
    private int charges;
    private int maxCharges;

    private int healDuration;
    private int healTimer;

    public HealAbility(Player player) {
        super("Heal", "heal_icon", 0);
        this.player = player;
        this.charges = 1;
        this.maxCharges = 5;
        healDuration = 300;
        healTimer = 0;
    }

    @Override
    public void activate() {
       if(charges > 0) {
            player.heal();
            healTimer = healDuration;

            SoundManager.getInstance().playClip("heal", false);
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

    public void useCharge() {
        charges --;
        isAvailable = charges > 0;
    }

    public int getMaxCharges() {
        return maxCharges;
    }
    
}
