package com.cebbys.slabref.content.blocks.properties;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.cebbys.slabref.registries.SlabRegistry;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Blocks;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IdentifierProperty extends Property<Identifier> {

	private final ImmutableSet<Identifier> values;
	public static final String SPLIT = "_sp11t_";

	public static IdentifierProperty of(String name) {
		return new IdentifierProperty(name);
	}

	protected IdentifierProperty(String name) {
		super(name, Identifier.class);
		this.values = this.getIdentifierValues();
	}

	@Override
	public Collection<Identifier> getValues() {
		return this.values;
	}

	@Override
	public String name(Identifier value) {
		String[] parts = value.toString().split(":");
		String name = parts[0];
		for (int i = 1; i < parts.length; i++) {
			name = name + SPLIT + parts[i];
		}
		return name;
	}

	@Override
	public Optional<Identifier> parse(String name) {
		String[] parts = name.split(SPLIT);
		String idString = parts[0];
		for (int i = 1; i < parts.length; i++) {
			idString = idString + ":" + parts[i];
		}
		Identifier id = new Identifier(idString);
		return this.values.contains(id) ? Optional.of(id) : Optional.empty();
	}

	private ImmutableSet<Identifier> getIdentifierValues() {
		Set<Identifier> set = new HashSet<Identifier>(SlabRegistry.SLABS);
		set.remove(Registry.BLOCK.getId(Blocks.PETRIFIED_OAK_SLAB));
		return ImmutableSet.copyOf(set);
	}
}
