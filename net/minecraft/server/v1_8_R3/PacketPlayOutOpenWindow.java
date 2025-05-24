package net.minecraft.server.v1_8_R3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketPlayOutOpenWindow
        implements Packet<PacketListenerPlayOut> {
    private static Gson GSON = new GsonBuilder().create();
    private int a;
    private String b;
    private IChatBaseComponent c;
    private int d;
    private int e;
    private Map<String, String> f = new HashMap<String, String>();

    public PacketPlayOutOpenWindow() {
    }

    public PacketPlayOutOpenWindow(int i, String s, IChatBaseComponent comp, int j, Map<String, String> extra) {
        this.a = i;
        this.b = s;
        this.c = comp;
        this.d = j;
        this.f = extra;
    }

    public PacketPlayOutOpenWindow(int windowId, String inventoryType, IChatBaseComponent title, int slots) {
        this(windowId, inventoryType, title, slots, null);
    }

    @Override
    public void a(PacketListenerPlayOut listener) {
        listener.a(this);
    }

    @Override
    public void a(PacketDataSerializer serializer) throws IOException {
        this.a = serializer.readUnsignedByte();
        this.b = serializer.c(32);
        this.c = serializer.d();
        this.d = serializer.readUnsignedByte();
        if ("EntityHorse".equals(this.b)) {
            this.e = serializer.readInt();
        }
        String jsonString = serializer.c(Short.MAX_VALUE);
        this.f = GSON.fromJson(jsonString, HashMap.class);
    }

    @Override
    public void b(PacketDataSerializer serializer) throws IOException {
        serializer.writeByte(this.a);
        serializer.a(this.b);
        serializer.a(this.c);
        serializer.writeByte(this.d);
        if (this.f != null && !this.f.isEmpty()) {
            serializer.a(GSON.toJson(this.f));
        }
    }

    public void send(Player player) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(this);
    }
}