package unsw.loopmania.Types;

/**
 * Type of enemies
 */
public enum EnemyType{
    SLUG(2, 2, 10, 5, 0.1, 1.5, 10),
    ZOMBIE(3, 3, 10, 10, 0.1, 0, 100),
    VAMPIRE(3, 4, 20, 20, 0.2, 2, 500),
    DOGGIE(2, 2, 50, 25, 0.1, 2, 1000),
    ELAN_MUSKE(2, 2, 100, 30, 0.1, 2, 5000);

    private int battleRadius;
    private int supportRadius;
    private double maxHealth;
    private int attack;
    private double critChance;
    private double critMult;
    private int experienceReward;

    EnemyType(int battleRadius, int supportRadius, double maxHealth, int attack, double critChance, double critMult, int experienceReward){
        this.battleRadius = battleRadius;
        this.supportRadius = supportRadius;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.critChance = critChance;
        this.critMult = critMult;
        this.experienceReward = experienceReward;
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

    public int getExperienceReward(){
        return experienceReward;
    }
}