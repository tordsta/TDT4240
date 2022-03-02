package com.tdt4240.game.ecs.system.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.tdt4240.game.ecs.component.Texture;

import java.util.Comparator;

// TODO: documentation
public class ZIndexComparator implements Comparator<Entity> {
    private ComponentMapper<Texture> textureMapper = ComponentMapper.getFor(Texture.class);

    @Override
    public int compare(Entity e1, Entity e2) {
        final int z1 = textureMapper.get(e1).getZIndex();
        final int z2 = textureMapper.get(e2).getZIndex();

        return Integer.compare(z1, z2);
    }
}
