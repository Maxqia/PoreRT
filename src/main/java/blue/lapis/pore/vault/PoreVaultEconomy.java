/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
 * Copyright (c) Vault API LGPLv3
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

package blue.lapis.pore.vault;

import static blue.lapis.pore.util.PoreText.convert;
import static blue.lapis.pore.vault.PoreVaultPermissions.getContextByWorldName;

import blue.lapis.pore.Pore;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PoreVaultEconomy extends AbstractEconomy {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "Sponge";
    }

    public static EconomyService getEconomy() {
        return Pore.getGame().getServiceManager()
                .getRegistration(EconomyService.class)
                .get().getProvider();
    }

    /**
     * Get's the default currency (short hand)
     */
    public static Currency getCurrency() {
        return getEconomy().getDefaultCurrency();
    }

    public static Account getAccount(String playerName) {
        return getEconomy().getOrCreateAccount(playerName).get();
    }

    public static Cause generateCause() {
        return Cause.of(NamedCause.source(Pore.getPlugin()));
    }

    // -- start banks -- // (taken from vault's essentials economy)
    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support bank accounts!");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }
    // -- end banks -- //


    @Override
    public int fractionalDigits() {
        return getCurrency().getDefaultFractionDigits();
    }

    @Override
    public String format(double amount) {
        return convert(getCurrency().format(BigDecimal.valueOf(amount)));
    }

    @Override
    public String currencyNamePlural() {
        return convert(getCurrency().getPluralDisplayName());
    }

    @Override
    public String currencyNameSingular() {
        return convert(getCurrency().getDisplayName());
    }

    @Override
    public boolean hasAccount(String playerName) {
        return getEconomy().hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName); // no contexts for hasAccount
    }

    @Override
    public double getBalance(String playerName) {
        return getAccount(playerName).getBalance(getCurrency()).doubleValue();
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getAccount(playerName).getBalance(getCurrency(), getContextByWorldName(world)).doubleValue();
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return getBalance(playerName, worldName) >= amount;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return resultConverter(getAccount(playerName)
                .withdraw(getCurrency(), BigDecimal.valueOf(amount),
                        generateCause()));
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return resultConverter(getAccount(playerName)
                .withdraw(getCurrency(), BigDecimal.valueOf(amount),
                        generateCause(), getContextByWorldName(worldName)));
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return resultConverter(getAccount(playerName).deposit(getCurrency(),
                BigDecimal.valueOf(amount), generateCause()));
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return resultConverter(getAccount(playerName).deposit(getCurrency(),
                BigDecimal.valueOf(amount), generateCause(), getContextByWorldName(worldName)));
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return getEconomy().getOrCreateAccount(playerName).isPresent();
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName); // no contexts for createPlayerAccount
    }

    public static EconomyResponse resultConverter(TransactionResult type) {
        ResponseType convertedType = ResponseType.FAILURE;
        if (type.getType().equals(ResultType.SUCCESS)) {
            convertedType = ResponseType.SUCCESS;
        }
        return new EconomyResponse(type.getAmount().doubleValue(),
                type.getAccount().getBalance(getCurrency()).doubleValue(),
                convertedType, type.getType().getName());
    }

}
