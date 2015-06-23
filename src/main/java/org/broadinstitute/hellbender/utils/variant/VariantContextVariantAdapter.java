package org.broadinstitute.hellbender.utils.variant;

import htsjdk.variant.variantcontext.VariantContext;

import java.io.Serializable;
import java.util.UUID;

/**
 * VariantContextVariantAdapter wraps the existing htsjdk VariantContext class so it can be
 * used with the Variant API.
 */
public class VariantContextVariantAdapter implements Variant, Serializable {
    private static final long serialVersionUID = 1L;

    final private VariantContext variantContext;
    private UUID uuid;

    public VariantContextVariantAdapter(VariantContext vc) {
        this.variantContext = vc;
        this.uuid = UUID.randomUUID();
    }
    @Override
    public String getContig() { return variantContext.getContig(); }
    @Override
    public int getStart() { return variantContext.getStart(); }
    @Override
    public int getEnd() { return variantContext.getEnd(); }
    @Override
    public boolean isSnp() { return variantContext.isSNP(); }
    @Override
    public boolean isIndel() { return variantContext.isIndel(); }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    // For testing purposes only.
    public void clearUUID() {
        this.uuid = new UUID(0L, 0L);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariantContextVariantAdapter that = (VariantContextVariantAdapter) o;

        // VariantContext doesn't define equality, so we have to.
        if (!getContig().equals(that.getContig())) {
            return false;
        }
        if (getStart() != that.getStart()) {
            return false;
        }
        if (getEnd() != that.getEnd()) {
            return false;
        }
        if (isSnp() != that.isSnp()) {
            return false;
        }
        if (isIndel() != that.isIndel()) {
            return false;
        }
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        int result = variantContext.hashCode();
        result = 31 * result + uuid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("VariantContextVariantAdapter -- interval(%s:%d-%d), snp(%b), indel(%b), uuid(%d,%d)",
                getContig(), getStart(), getEnd(), isSnp(), isIndel(), getUUID().getLeastSignificantBits(), getUUID().getMostSignificantBits());
    }
}
