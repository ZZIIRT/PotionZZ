package org.zzii.potionzz.util;

import java.util.List;
import java.util.stream.Collectors;

public final class ChatUtils {

    private ChatUtils() {}

    public static String colorize(String text) {
        return text == null ? null : text.replace('&', '§');
    }

    public static List<String> colorize(List<String> lines) {
        return lines.stream().map(ChatUtils::colorize).collect(Collectors.toList());
    }
}
