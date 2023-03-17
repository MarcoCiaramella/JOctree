package com.lib.joctree;

import static org.junit.Assert.assertEquals;

import com.lib.joctree.math.Octree;
import com.lib.joctree.math.Vector3;
import com.lib.joctree.math.collision.BoundingBox;
import com.lib.joctree.math.collision.Ray;
import com.lib.joctree.utils.ObjectSet;

import org.junit.Test;

public class UnitTest {

    public static final int MAX_DEPTH = 8;
    public static final int MAX_ITEMS_PER_NODE = 200;

    @Test
    public void test() {
        Vector3 min = new Vector3(-10, -10, -10);
        Vector3 max = new Vector3(10, 10, 10);
        Octree<GameObject> octree = new Octree<GameObject>(min, max, MAX_DEPTH, MAX_ITEMS_PER_NODE, new Octree.Collider<GameObject>() {
            @Override
            public boolean intersects (BoundingBox nodeBounds, GameObject geometry) {
                return nodeBounds.intersects(geometry.box);
            }
            @Override
            public float intersects (Ray ray, GameObject geometry) {
        		/*if (Intersector.intersectRayBounds(ray, geometry.box, new Vector3())) {
        			return tmp.dst2(ray.origin);
        		}
        		return Float.MAX_VALUE;*/
                return Float.MAX_VALUE;
            }
        });
        GameObject gameObject1 = new GameObject();
        gameObject1.box = new BoundingBox();
        gameObject1.box.max.set(1,1,1);
        gameObject1.box.min.set(-1,-1,-1);

        GameObject gameObject2 = new GameObject();
        gameObject2.box = new BoundingBox();
        gameObject2.box.max.set(4,8,5);
        gameObject2.box.min.set(2,2,2);

        GameObject gameObject3 = new GameObject();
        gameObject3.box = new BoundingBox();
        gameObject3.box.max.set(10,10,10);
        gameObject3.box.min.set(-10,-10,-10);

        // Adding game objects to the octree
        octree.add(gameObject1);
        octree.add(gameObject2);

        // Querying the result
        ObjectSet<GameObject> result = new ObjectSet<>();
        octree.query(gameObject3.box, result);

        assertEquals(2, result.size);
    }

    static class GameObject {
        BoundingBox box;

        @Override
        public String toString() {
            return String.format("bbox max %f %f %f min %f %f %f", box.max.x, box.max.y, box.max.z, box.min.x, box.min.y, box.min.z);
        }
    }
}