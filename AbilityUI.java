import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class AbilityUI {
    private List<Ability> abilities;
    private int x,y;
    private int iconSize;
    private int padding;
    
    public AbilityUI(int x, int y) {
        this.x = x;
        this.y = y;
        this.iconSize = 32;
        this.padding = 10;
        this.abilities = new ArrayList<>();
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public void draw(Graphics g) {
        int currentX = x;

        for (Ability ability : abilities) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(currentX, y, iconSize, iconSize);

            Image icon =  ImageManager.getImage(ability.getIconName());
            if(icon != null) {
                g.drawImage(icon, currentX, y, iconSize, iconSize, null);
            }

            if(!ability.isAvailable()) {
                g.setColor(new Color(0,0,0,150));

                if(ability instanceof HealAbility) {

                    g.fillRect(currentX, y, iconSize, iconSize);
                } else {
                    float cooldownPercent = (float) ability.getCooldown() / (float) ability.getMaxCooldown();
                    int cooldownHeight = (int) (iconSize * cooldownPercent);
                    g.fillRect(currentX, y, iconSize, cooldownHeight);
                } 
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD,10));
            g.drawString(ability.getName(), currentX, y + iconSize + 12);

            if (ability instanceof HealAbility) {
                HealAbility healAbility = (HealAbility) ability;
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 10));
                g.drawString("" + healAbility.getCharges(), currentX + 5, y + iconSize - 5);
            }

            currentX += iconSize + padding;
        }
    }
}
