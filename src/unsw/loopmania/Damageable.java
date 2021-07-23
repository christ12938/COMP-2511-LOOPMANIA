package unsw.loopmania;

import unsw.loopmania.Types.DamageableType;

public interface Damageable {
    public void takeDamage(double damage, Damageable from);
    
    public void dealDamage(Damageable damageable);

    public boolean isDefeated();

    public DamageableType getDamageableType();
}
