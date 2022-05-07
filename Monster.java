
// TODO: Copy code from your Assignment 1 as starter code.
public class Monster extends Unit {
	
	/** 
	* Default constructor initialises monster attributes to the below values when monster is created
	*/ 
	public Monster() { 
		this.name = "[None]";
		this.maxHealth = maxHealth;
		this.damage = damage;
		this.X = 4;
		this.Y = 2;
	}

	public String getName() {
		return name;
	}
	public void setName(String monsterName) {
		this.name = monsterName;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int health) {
		this.currentHealth = health;
	}

	/** 
	* Default name is set as "[None]" until monster name is input by the user
	*/
	public boolean isCreated() {
		if(!name.equals("[None]")) {
			return true; 
		}
		return false;
	}

	public void setIsCreated() {
		this.isCreated = true;
	}

	public boolean getIsCreated() {
		return isCreated;
	}
}

