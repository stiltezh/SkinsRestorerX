/*
 * SkinsRestorer
 *
 * Copyright (C) 2022 SkinsRestorer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package net.skinsrestorer.sponge.utils;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.interfaces.ISRCommandSender;
import net.skinsrestorer.api.interfaces.ISRPlayer;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public class WrapperSponge {
    public static ISRCommandSender wrapCommandSender(CommandContext sender) {
        return new ISRCommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(Identity.nil(), LegacyComponentSerializer.legacySection().deserialize(message));
            }

            @Override
            public String getName() {
                return sender.identifier().name();
            }

            @Override
            public boolean hasPermission(String permission) {
                return sender.hasPermission(permission);
            }

            @Override
            public boolean isConsole() {
                return sender instanceof ConsoleSource;
            }
        };
    }

    public static ISRPlayer wrapPlayer(ServerPlayer player) {
        return new ISRPlayer() {
            @Override
            public PlayerWrapper getWrapper() {
                return new PlayerWrapper(player);
            }

            @Override
            public String getName() {
                return player.name();
            }

            @Override
            public void sendMessage(String message) {
                player.sendMessage(LegacyComponentSerializer.legacySection().deserialize(message));
            }

            @Override
            public boolean hasPermission(String permission) {
                return player.hasPermission(permission);
            }
        };
    }
}
