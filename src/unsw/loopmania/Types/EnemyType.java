package unsw.loopmania.Types;

/**
 * Type of enemies
 */
public enum EnemyType{
    SLUG(2, 2, 10, 5, 0.1, 1.5),
    ZOMBIE(3, 3, 10, 10, 0.1, 0),
    VAMPIRE(3, 4, 20, 20, 0.2, 2);

    private int battleRadius;
    private int supportRadius;
    private double maxHealth;
    private int attack;
    private double critChance;
    private double critMult;

    EnemyType(int battleRadius, int supportRadius, double maxHealth, int attack, double critChance, double critMult){
        this.battleRadius = battleRadius;
        this.supportRadius = supportRadius;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.critChance = critChance;
        this.critMult = critMult;
    }

    public int getBattleRadius(){
        return battleRadius;
    }

    public int getSupportRadius(){
        return supportRadius;
    }

    public double getMaxHealth(){
        return maxHealth;
    }

    public int getAttack(){
        return attack;
    }

    public double getCritChance(){
        return critChance;
    }

    public double getCritMultiplier(){
        return critMult;
    }
}