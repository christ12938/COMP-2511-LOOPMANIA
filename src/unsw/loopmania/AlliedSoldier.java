package unsw.loopmania;

public class AlliedSoldier implements Damageable{
    private int health;
    private int damage;
    private static int count = 0;

    public AlliedSoldier () {
        count++;
        this.health = 30;
        this.damage = 5;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void dealDamage(Damageable damageable) {
        damageable.takeDamage(damage);
    }
}
