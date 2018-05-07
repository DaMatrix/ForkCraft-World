/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2018 DaPorkchop_ and contributors
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.forkcraft.world.chunk.storage;

import net.daporkchop.lib.buffer.PorkBuf;

/**
 * A 4-bit set of flags for a 16³ block storage
 *
 * @author DaPorkchop_
 */
public class MetaLayer {
    private volatile int[] data;

    public MetaLayer() {
        this.reset();
    }

    public MetaLayer(PorkBuf buf) {
        this.reset();
        this.load(buf);
    }

    public void load(PorkBuf buf) {
        if (this.data == null) {
            this.reset();
        }

        for (int i = 0; i < 512; i++) {
            this.data[i] = buf.getInt();
        }
    }

    public byte[] toBytes() {
        EMPTY:
        {
            for (int i = 0; i < 512; i++) {
                if (this.data[i] != 0) {
                    break EMPTY;
                }
            }
            return null;
        }
        PorkBuf buf = PorkBuf.allocate(2048);
        for (int i = 0; i < 512; i++) {
            buf.putInt(this.data[i]);
        }
        return buf.toArray();
    }

    public void reset() {
        this.data = new int[512];
    }

    public int get(int x, int y, int z) {
        return (this.data[this.getIndex(x, y, z)] >> (y & 7)) & 0xF;
    }

    public void set(int x, int y, int z, int val) {
        int i = this.getIndex(x, y, z);
        int curr = (this.data[i] >> (y & 7)) & 0xF;
        this.data[i] ^= ((curr ^ (val & 0xF)) << (y & 7));
    }

    private int getIndex(int x, int y, int z) {
        return ((y >> 3) << 8) | (x << 4) | z;
    }
}
