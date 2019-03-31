package org.sonar.sslr.grammar;

import org.sonar.sslr.internal.matchers.Matcher;
import org.sonar.sslr.internal.vm.Machine;
import org.sonar.sslr.internal.vm.NativeExpression;

public class CaseInsensitiveStringExpression extends NativeExpression implements Matcher {

  private final String string;

  public CaseInsensitiveStringExpression(String string) {
    this.string = string;
  }

  @Override
  public void execute(Machine machine) {
    if (machine.length() < string.length()) {
      machine.backtrack();
      return;
    }
    for (int i = 0; i < string.length(); i++) {
      if (Character.toLowerCase(machine.charAt(i)) != Character.toLowerCase(string.charAt(i))) {
        machine.backtrack();
        return;
      }
    }
    machine.createLeafNode(this, string.length());
    machine.jump(1);
  }

  @Override
  public String toString() {
    return "String " + string;
  }

}