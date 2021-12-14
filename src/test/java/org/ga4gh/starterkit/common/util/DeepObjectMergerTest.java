package org.ga4gh.starterkit.common.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DeepObjectMergerTest {

    private static class Simple {
        private String s;
        private LocalDateTime dt;
        private boolean b;

        public Simple(String s, LocalDateTime dt, boolean b) {
            this.s = s;
            this.dt = dt;
            this.b = b;
        }

        public String getS() {
            return s;
        }

        public LocalDateTime getDt() {
            return dt;
        }

        public boolean isB() {
            return b;
        }
    }

    private static class SimpleChild extends Simple {

        public SimpleChild(String s, LocalDateTime dt, boolean b) {
            super(s, dt, b);
        }
    }

    private static class Complex {
        private Simple s;

        public Complex(Simple s) {
            this.s = s;
        }

        public Simple getS() {
            return s;
        }
    }

    @Test
    public void testNullPrimitiveSourceIgnored() {
        LocalDateTime dt = LocalDateTime.now();

        Simple target = new Simple("s", dt, false);
        Simple source = new Simple(null, null, true);

        DeepObjectMerger merger = new DeepObjectMerger();
        merger.merge(source, target);

        Assert.assertEquals(target.getS(), "s");
        Assert.assertEquals(target.getDt(), dt);
        Assert.assertTrue(target.isB());
    }

    @Test
    public void testNonNullPrimitiveSourceCopied() {
        LocalDateTime dt = LocalDateTime.now();

        Simple target = new Simple("s", null, false);
        Simple source = new Simple("t", dt, true);

        DeepObjectMerger merger = new DeepObjectMerger();
        merger.merge(source, target);

        Assert.assertEquals(target.getS(), "t");
        Assert.assertEquals(target.getDt(), dt);
        Assert.assertTrue(target.isB());
    }

    @Test
    public void testNullComplexSourceIgnored() {
        LocalDateTime dt = LocalDateTime.now();

        Simple simpleTarget = new Simple("s", dt, true);

        Complex complexTarget = new Complex(simpleTarget);
        Complex complexSource = new Complex(null);

        DeepObjectMerger merger = new DeepObjectMerger();
        merger.merge(complexSource, complexTarget);

        Simple mergedTarget = complexTarget.getS();

        Assert.assertEquals(mergedTarget.getS(), "s");
        Assert.assertEquals(mergedTarget.getDt(), dt);
        Assert.assertTrue(mergedTarget.isB());
    }

    @Test
    public void testNonNullComplexSourceMerged() {
        LocalDateTime dt = LocalDateTime.now();

        Simple simpleTarget = new Simple("s", null, false);
        Simple simpleSource = new Simple("t", dt, true);

        Complex complexTarget = new Complex(simpleTarget);
        Complex complexSource = new Complex(simpleSource);

        DeepObjectMerger merger = new DeepObjectMerger();
        merger.merge(complexSource, complexTarget);

        Simple mergedTarget = complexTarget.getS();

        Assert.assertEquals(mergedTarget.getS(), "t");
        Assert.assertEquals(mergedTarget.getDt(), dt);
        Assert.assertTrue(mergedTarget.isB());
    }

    private static class HasList {
        List<String> strings;

        public HasList(List<String> strings) {
            this.strings = strings;
        }

        public List<String> getStrings() {
            return strings;
        }
    }

    @Test
    public void testCollectionsCopied() {
        HasList target = new HasList(Arrays.asList("a", "b"));
        HasList source = new HasList(Arrays.asList("c", "d"));

        DeepObjectMerger merger = new DeepObjectMerger();
        merger.merge(source, target);

        Assert.assertEquals(target.getStrings().get(0), "c");
        Assert.assertEquals(target.getStrings().get(1), "d");
    }

    @Test
    public void testParentClassPropsCopied() {

        LocalDateTime dt = LocalDateTime.now();

        SimpleChild target = new SimpleChild("s", null, false);
        SimpleChild source = new SimpleChild("t", dt, true);

        DeepObjectMerger merger = new DeepObjectMerger();
        merger.merge(source, target);

        Assert.assertEquals(target.getS(), "t");
        Assert.assertEquals(target.getDt(), dt);
        Assert.assertTrue(target.isB());
    }

    private List<Complex> atomicClassConstruct() {
        LocalDateTime dt = LocalDateTime.now();
        SimpleChild source = new SimpleChild(null, null, true);
        SimpleChild target = new SimpleChild("s", dt, false);
        Complex sourceComplex = new Complex(source);
        Complex targetComplex = new Complex(target);
        List<Complex> complexes = new ArrayList<>() {{
            add(sourceComplex);
            add(targetComplex);
        }};
        return complexes;
    }

    private void atomicClassAssertions(Complex targetComplex) {
        Assert.assertEquals(targetComplex.getS().getS(), null);
        Assert.assertEquals(targetComplex.getS().getDt(), null);
        Assert.assertTrue(targetComplex.getS().isB());
    }

    @Test
    public void testAddAtomicClass() {
        DeepObjectMerger merger = new DeepObjectMerger(true);
        merger.addAtomicClass(Simple.class);
        merger.addAtomicClass(SimpleChild.class);
        
        List<Complex> complexes = atomicClassConstruct();
        merger.merge(complexes.get(0), complexes.get(1));
        atomicClassAssertions(complexes.get(1));

        merger.removeAtomicClass(Simple.class);
        merger.removeAtomicClass(SimpleChild.class);
        merger.addAtomicClasses(new HashSet<>() {{
            add(Simple.class);
            add(SimpleChild.class);
        }});

        complexes = atomicClassConstruct();
        merger.merge(complexes.get(0), complexes.get(1));
        atomicClassAssertions(complexes.get(1));

        merger.removeAtomicClasses(new HashSet<>() {{
            add(Simple.class);
            add(SimpleChild.class);
        }});
        merger.setAtomicClasses(new HashSet<>() {{
            add(Simple.class);
            add(SimpleChild.class);
        }});

        complexes = atomicClassConstruct();
        merger.merge(complexes.get(0), complexes.get(1));
        atomicClassAssertions(complexes.get(1));
    }
}
