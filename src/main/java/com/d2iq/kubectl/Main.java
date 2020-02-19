package com.d2iq.kubectl;


import picocli.CommandLine;


public final class Main {

    public static void main(String[] args) {
        new CommandLine(new JavaKubeCtlCommand()).execute(args);
    }
}
