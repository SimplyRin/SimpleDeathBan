package net.simplyrin.simpledeathban;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;

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
@AllArgsConstructor
public class DeathBanCommand implements CommandExecutor {

	private Main instance;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0) {
			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("add")) {
					Player player = this.instance.getServer().getPlayer(args[1]);
					if (player == null) {
						sender.sendMessage("§cエラー: このプレイヤーは見つかりませんでした。");
						return true;
					}
					if (this.instance.getPlayers().contains(player.getName())) {
						sender.sendMessage("§cエラー: このプレイヤーは既に追加されています。");
						return true;
					}

					this.instance.getPlayers().add(player.getName());
					sender.sendMessage("§a" + player.getName() + " を DeathBan に追加しました。");
					return true;
				}
				if (args[0].equalsIgnoreCase("remove")) {
					Player player = this.instance.getServer().getPlayer(args[1]);
					if (player == null) {
						sender.sendMessage("§cエラー: このプレイヤーは見つかりませんでした。");
						return true;
					}
					if (!this.instance.getPlayers().contains(player.getName())) {
						sender.sendMessage("§cエラー: このプレイヤーはまだ追加されていません。");
						return true;
					}

					this.instance.getPlayers().remove(player.getName());
					sender.sendMessage("§a" + player.getName() + " を DeathBan から削除しました。");
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("list")) {
				List<String> players = this.instance.getPlayers();
				if (players == null || players.isEmpty()) {
					sender.sendMessage("§cエラー: DeathBan にはまだ誰も追加されていません。");
					return true;
				}
				sender.sendMessage("§a登録されているユーザー:");
				for (String name : players) {
					sender.sendMessage("§a- " + name);
				}
			}
		}

		sender.sendMessage("§c使用方法:");
		sender.sendMessage("§c/" + command.getName() + " add <名前>: DeathBan にユーザーを追加します。");
		sender.sendMessage("§c/" + command.getName() + " remove <名前>: DeathBan からユーザーを削除します。");
		sender.sendMessage("§c/" + command.getName() + " list: 登録されているユーザーを確認します。");
		return true;
	}

}
