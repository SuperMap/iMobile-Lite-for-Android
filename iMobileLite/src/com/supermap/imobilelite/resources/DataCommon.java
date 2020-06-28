package com.supermap.imobilelite.resources;

import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.LocaleData;

@BaseName("com.supermap.imobilelite.DataCommon")
@LocaleData(
        defaultCharset = "UTF-8",
        value = { @Locale("zh_CN")}
        )
public enum DataCommon {
    EDITFEATUREACTION_DOUBLECLICK,
    DATA_EXCEPTION    
}
