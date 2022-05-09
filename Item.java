
// TODO: Implement this class.

public class Item extends Entity {
    private char symbol;

	public Item() {
		
	}
	
	public void readSymbol(Player player) {
		// checks symbol of item and calls method that affects player health, damage or level 
		if(symbol == '+') {
			healPlayer(player);
		} else if(symbol == '^') {
			attackUp(player);
		} else if(symbol == '@') {
			levelUp(player);
		}
	}
	
	public void healPlayer(Player player) {
		player.setCurrentHealth(player.getMaxHealth()); // resets player health to maxHealth
		System.out.println("Healed!");
	}
	
	public void attackUp(Player player) {
		// increase player damage value by 1
		player.setDamage(player.getDamage() + 1);
		System.out.println("Attack Up!");
	}
	
	public void levelUp(Player player) {
        // increases players current level by 1
		player.setLevel(player.getLevel() + 1);
		System.out.println("World complete! (You leveled up!)");
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
}
