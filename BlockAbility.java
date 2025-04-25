public class BlockAbility extends Ability{
    private Player player;
    private int blockDuration;
    private int blockTimer;


    public BlockAbility(Player player) {
        super("Block", "block_icon", 120);
        this.player = player;
        this.blockDuration = 100;
        this.blockTimer = 0;
       
    }

    @Override
    public void activate() {
       if(isAvailable) {
            player.setBlocking(true);
            blockTimer = blockDuration;
            isAvailable = false;
            cooldown = maxCooldown;
            SoundManager.getInstance().playClip("block", false);
       }
    }

    @Override
    public void update() {
        super.update();

        if(player.isBlocking() && blockTimer > 0) {
            blockTimer --;
            if(blockTimer <= 0) {
                player.setBlocking(false);
            }
        }
    }
    
}
