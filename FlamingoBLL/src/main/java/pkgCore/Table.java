package pkgCore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Table implements Serializable{

	private UUID TableID;
	private HashMap<UUID, Player> hmTablePlayer = new HashMap<UUID,Player>();
	
	public Table() {
		super();
		this.TableID = UUID.randomUUID();
	}
	
	public void AddPlayerToTable(Player p)
	{
		hmTablePlayer.put(p.getPlayerID(), p);
	}
	public void RemovePlayerFromTable(Player p)
	{
		hmTablePlayer.remove(p.getPlayerID());
	}
	
	public Player GetPlayerFromTable(Player p)
	{
		return (Player)hmTablePlayer.get(p.getPlayerID());

	}

	public HashMap<UUID, Player> getHmTablePlayer() {
		return hmTablePlayer;
	}
	public void setHmTablePlayer(HashMap<UUID, Player> hmTablePlayer) {
		this.hmTablePlayer = hmTablePlayer;
	}
}
