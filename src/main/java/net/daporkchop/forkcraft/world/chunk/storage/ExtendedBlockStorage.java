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
 * A 16³ storage for blocks
 *
 * @author DaPorkchop_
 */
public class ExtendedBlockStorage {
    private volatile MetaLayer blockMeta = new MetaLayer();
    private volatile MetaLayer blockLight = new MetaLayer();
    private volatile MetaLayer skyLight = new MetaLayer();

    public ExtendedBlockStorage(PorkBuf buf) {
        this.load(buf);
    }

    public void load(PorkBuf buf) {
        boolean doBlocks;
        boolean doMeta;
        boolean doBlockLight;
        boolean doSkyLight;
        boolean doEntities;
        boolean doTileEntities;

        {
            int flags = buf.getByte() & 0xFF;
            doBlocks = (flags & 1) == 1;
            doMeta = ((flags >> 1) & 1) == 1;
            doBlockLight = ((flags >> 2) & 1) == 1;
            doSkyLight = ((flags >> 3) & 1) == 1;
            doEntities = ((flags >> 4) & 1) == 1;
            doTileEntities = ((flags >> 5) & 1) == 1;
        }

        if (doBlocks) {

        }

        if (doMeta) {
            if (this.blockMeta == null) {
                this.blockMeta = new MetaLayer(buf);
            } else {
                this.blockMeta.load(buf);
            }
        } else {
            this.blockMeta = null;
        }

        if (doBlockLight) {
            if (this.blockLight == null) {
                this.blockLight = new MetaLayer(buf);
            } else {
                this.blockLight.load(buf);
            }
        } else {
            this.blockLight = null;
        }

        if (doSkyLight) {
            if (this.skyLight == null) {
                this.skyLight = new MetaLayer(buf);
            } else {
                this.skyLight.load(buf);
            }
        } else {
            this.skyLight = null;
        }
    }

    public void reset() {
        if (this.blockMeta != null) {
            this.blockMeta.reset();
        }
        if (this.blockLight != null) {
            this.blockLight.reset();
        }
        if (this.skyLight != null) {
            this.skyLight.reset();
        }
    }
}
