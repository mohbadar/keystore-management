package de.adorsys.keymanagement.api.types.template;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public class NameAndPassword implements KeyTemplate {

    @NonNull
    private final KeyNamingStrategy namingStrategy;
    private final Supplier<char[]> password;

    public NameAndPassword(String alias, @NonNull Supplier<char[]> password) {
        this.namingStrategy = new DefaultNamingStrategy(alias, null);
        this.password = password;
    }

    public NameAndPassword(@NonNull Supplier<char[]> password) {
        this.namingStrategy = new DefaultNamingStrategy(null, null);
        this.password = password;
    }

    @Override
    public String generateName() {
        return namingStrategy.generateName();
    }
}
