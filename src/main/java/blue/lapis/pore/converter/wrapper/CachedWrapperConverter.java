/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016-2017, Maxqia <https://github.com/Maxqia> AGPLv3
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue> MIT
 * Copyright (c) Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * An exception applies to this license, see the LICENSE file in the main directory for more information.
 */

package blue.lapis.pore.converter.wrapper;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import blue.lapis.pore.Pore;
import blue.lapis.pore.util.constructor.PoreConstructors;
import blue.lapis.pore.util.constructor.SimpleClassConstructor;

import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

final class CachedWrapperConverter<B> implements Function<Object, B> {

    private final LoadingCache<Object, Object> cache = CacheBuilder.newBuilder()
            .weakKeys()
            .build(new CacheLoader<Object, Object>() {
                @Override
                public Object load(Object handle) {
                    return create(handle);
                }
            });

    private final LoadingCache<Class<?>, Converter<?, ? extends B>> classCache = CacheBuilder.newBuilder()
            .weakKeys()
            .build(new CacheLoader<Class<?>, Converter<?, ? extends B>>() {
                @Override
                public Converter<?, ? extends B> load(Class<?> sponge) {
                    return find(sponge);
                }
            });

    final ImmutableMap<Class<?>, Converter<?, ? extends B>> registry;

    @SuppressWarnings("unchecked")
    private CachedWrapperConverter(Class<B> base, Map<Class<? extends B>, Class<?>> registrations) {
        Set<Class<? extends B>> registered = Sets.newHashSet();
        Set<Map.Entry<Class<? extends B>, Class<?>>> parents = Sets.newLinkedHashSet();
        Multimap<Class<? extends B>, Map.Entry<Class<? extends B>, Class<?>>> children = LinkedHashMultimap.create();

        for (Map.Entry<Class<? extends B>, Class<?>> entry : registrations.entrySet()) {
            Class<?> parent = entry.getKey().getSuperclass();
            while (parent != base && !registered.contains(parent)) {
                parent = parent.getSuperclass();
            }

            registered.add(entry.getKey());

            if (parent == base) {
                parents.add(entry);
            } else {
                children.put((Class<? extends B>) parent, entry);
            }
        }

        ImmutableMap.Builder<Class<?>, Converter<?, ? extends B>> builder = ImmutableMap.builder();

        for (Map.Entry<Class<? extends B>, Class<?>> entry : parents) {
            builder.put(entry.getValue(), build(children, entry.getValue(), entry.getKey()));
        }

        this.registry = builder.build();
    }

    @SuppressWarnings("unchecked")
    private <S, P extends B> Converter<S, P> build(
            Multimap<Class<? extends B>, Map.Entry<Class<? extends B>, Class<?>>> childrenRegistry,
            Class<S> sponge, Class<P> pore) {

        // Create children converters
        Collection<Map.Entry<Class<? extends B>, Class<?>>> children = childrenRegistry.get(pore);

        ImmutableMap.Builder<Class<? extends S>, Converter<? extends S, ? extends P>> converterRegistry =
                ImmutableMap.builder();

        for (Map.Entry<Class<? extends B>, Class<?>> child : children) {
            Class<? extends S> childSponge = (Class<? extends S>) child.getValue();
            converterRegistry.put(childSponge,
                    build(childrenRegistry, childSponge, (Class<? extends P>) child.getKey()));
        }

        return new Converter<>(sponge, pore, converterRegistry.build());
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <P extends B> P get(Object handle) {
        if (handle == null) {
            return null;
        }

        return (P) cache.getUnchecked(handle);
    }

    @Nullable
    public <P extends B> P get(Class<?> type, Object handle) {
        return get(handle); // TODO: Optimize first access with type class as entry point
    }

    @Nullable
    @Override
    public B apply(@Nullable Object input) {
        return get(input);
    }

    private Object create(Object handle) {
        Converter<?, ? extends B> converter = classCache.getUnchecked(handle.getClass());
        return converter.applyUnchecked(handle);
    }

    private Converter<?, ? extends B> find(Class<?> sponge) {
        // Find a matching class
        for (Map.Entry<Class<?>, Converter<?, ? extends B>> entry : registry.entrySet()) {
            if (entry.getKey().isAssignableFrom(sponge)) {
                return entry.getValue().findUnchecked(sponge);
            }
        }

        throw new UnsupportedOperationException(sponge.toString());
    }

    static final class Converter<S, P> implements Function<S, P> {

        final SimpleClassConstructor<P, S> constructor;
        final ImmutableMap<Class<? extends S>, Converter<? extends S, ? extends P>> registry;

        private Converter(Class<S> sponge, Class<P> pore,
                ImmutableMap<Class<? extends S>, Converter<? extends S, ? extends P>> registry) {
            checkNotNull(sponge, "sponge");
            checkNotNull(pore, "pore");
            this.registry = checkNotNull(registry, "registry");
            this.constructor = PoreConstructors.create(pore, sponge);
        }

        public Converter<? extends S, ? extends P> find(Class<? extends S> sponge) {
            // Find the most specific type
            for (Map.Entry<Class<? extends S>, Converter<? extends S, ? extends P>> entry : registry.entrySet()) {
                if (entry.getKey().isAssignableFrom(sponge)) {
                    return entry.getValue().findUnchecked(sponge);
                }
            }

            return this;
        }

        @SuppressWarnings("unchecked")
        public Converter<? extends S, ? extends P> findUnchecked(Class<?> sponge) {
            return find((Class<? extends S>) sponge);
        }

        @Override
        public P apply(S input) {
            return constructor.apply(input);
        }

        @SuppressWarnings("unchecked")
        public P applyUnchecked(Object input) {
            return apply((S) input);
        }
    }

    static <B> Builder<B> builder(Class<B> base) {
        return new Builder<>(base);
    }

    static final class Builder<B> {

        private final Class<B> base;
        private final Set<Class<?>> registered = Sets.newHashSet();
        private final Map<Class<? extends B>, Class<?>> registry = Maps.newLinkedHashMap();

        private Builder(Class<B> base) {
            this.base = checkNotNull(base, "base");
        }

        public <S, P extends B> Builder<B> register(Class<S> sponge, Class<P> pore) {
            checkState(!registered.contains(checkNotNull(pore, "pore")), "Pore %s is already registered", pore);
            checkState(!registered.contains(checkNotNull(sponge, "sponge")), "Sponge %s is already registered", sponge);

            Class<?> parent = pore.getSuperclass();
            while (parent != base) {
                if (parent == Object.class || parent == null) {
                    throw new AssertionError(String.format("Pore %s does not extend the parent class %s", pore, base));
                }
                if (!registered.contains(parent)) {
                    Pore.getTestLogger().warn("Parent class {} for {} ({}) is not registered", parent.getSimpleName(),
                            pore.getSimpleName(), sponge.getSimpleName());
                }

                parent = parent.getSuperclass();
            }

            registered.add(pore);
            registered.add(sponge);
            registry.put(pore, sponge);
            return this;
        }

        public CachedWrapperConverter<B> build() {
            return new CachedWrapperConverter<>(base, registry);
        }
    }
}
