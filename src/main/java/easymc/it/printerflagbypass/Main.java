package easymc.it.printerflagbypass;

import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.expansion.FactionsKoreExpansion;
import easymc.it.printerflagbypass.features.PrinterFlagBypass;

public final class Main extends FactionsKoreExpansion {

    @Override
    public void onEnable() {
        this.registerFeature((new PrinterFlagBypass(FactionsKore.get(), "printer-flag-bypass", "bypass flags on printer", null, null)));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
