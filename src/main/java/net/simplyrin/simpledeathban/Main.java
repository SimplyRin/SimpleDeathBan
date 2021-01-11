package net.simplyrin.simpledeathban;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.BanList.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

/**
 * Created by SimplyRin on 2021/01/12.
 *
 * Copyright (c) 2021 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class Main extends JavaPlugin implements Listener {

	@Getter
	private List<String> players;

	@Override
	public void onEnable() {
		this.players = new ArrayList<>();
		this.getCommand("deathban").setExecutor(new DeathBanCommand(this));
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		this.players = null;
		this.getCommand("deathban").setExecutor(null);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player deathPlayer = event.getEntity();
		if (this.players.isEmpty() || !this.players.contains(deathPlayer.getName())) {
			return;
		}
		this.getServer().getScheduler().runTask(this, () -> {
			deathPlayer.kickPlayer("§c§l[DEATH BAN] §r§c死亡したため BAN されました。");
			this.getServer().getBanList(Type.NAME).addBan(deathPlayer.getName(), "\n\n§c§l[DEATH BAN]\n§r§c死亡したため BAN されました。", null, null);

			for (Player player : this.getServer().getOnlinePlayers()) {
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 2F);
				player.sendMessage("§c§l" + deathPlayer.getName() + " が死亡したためサーバーから BAN されました。");
			}
		});
		this.players.remove(deathPlayer.getName());
	}

}
