package main.java.com.excilys.sramirez.formation.computerdatabase.service;

@FunctionalInterface
public interface Function <A, B, R> {
//R is like Return, but doesn't have to be last in the list nor named R.
   public R apply (A a, B b);
}
