package com.c446.ironbound_core.registry;

import com.c446.ironbound_core.Ironbound;
import com.c446.ironbound_core.spells.SpellMindFlay;
import com.c446.ironbound_core.spells.SpellRewind;
import com.c446.ironbound_core.spells.SpellTimeFreeze;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class IronboundCoreSpells {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY, Ironbound.MOD_ID);
    public static final RegistryObject<AbstractSpell> MIND_FLAY;
    public static final RegistryObject<AbstractSpell> REWIND;
    public static final RegistryObject<AbstractSpell> TIME_FREEZE;

    static {
        MIND_FLAY = registerSpell(new SpellMindFlay());
        REWIND = registerSpell(new SpellRewind());
        TIME_FREEZE = registerSpell(new SpellTimeFreeze());
    }

    public static RegistryObject<AbstractSpell> registerSpell(@NotNull AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }
}
