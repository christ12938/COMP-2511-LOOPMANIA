package unsw.loopmania.Enemies;

public interface CritStrategy {

    public double applyCritDamage(int damage);

    public boolean isNextAttackCritical();
}
