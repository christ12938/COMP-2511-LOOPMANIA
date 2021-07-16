package unsw.loopmania;

public interface Damageable {
    public void takeDamage(int damage);
    
    public void dealDamage(Damageable damageable);
}
