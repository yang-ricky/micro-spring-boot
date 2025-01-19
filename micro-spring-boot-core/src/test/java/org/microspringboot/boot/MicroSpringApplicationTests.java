package org.microspringboot.boot;

import org.junit.Test;
import org.microspring.context.ApplicationContext;

@MicroSpringBootApplication
public class MicroSpringApplicationTests {

    @Test
    public void testApplicationStart() {
        ApplicationContext context = MicroSpringApplication.run(MicroSpringApplicationTests.class);
        assert context != null;
        ((org.microspring.context.support.AnnotationConfigApplicationContext)context).close();
    }
} 