package com.c446.ironbound_core.registry;

import com.c446.ironbound_core.spells.SpellRewind;
import org.jetbrains.annotations.NotNull;

import com.c446.ironbound_core.IronBound;
import com.c446.ironbound_core.spells.SpellMindFlay;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class IronboundCoreSpells {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY, IronBound.MOD_ID);
    public static final RegistryObject<AbstractSpell> MIND_FLAY;
    public static final RegistryObject<AbstractSpell> REWIND;

    public static RegistryObject<AbstractSpell> registerSpell(@NotNull AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }
    static {
        MIND_FLAY = registerSpell(new SpellMindFlay());
        REWIND = registerSpell(new SpellRewind());
    }
}
