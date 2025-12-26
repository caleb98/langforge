package net.calebscode.langforge.app.functional;

import java.util.function.Supplier;

import net.calebscode.langforge.app.data.SaveLoadModel;

@FunctionalInterface
public interface SaveLoadModelSupplier extends Supplier<SaveLoadModel> {

}
