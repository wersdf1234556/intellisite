package org.tonzoc.common;

import org.springframework.stereotype.Component;
import org.tonzoc.model.FenceItemModel;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@Component
public class PointSetHelper {

    public Boolean isItInTheFence (String pointLng, String pointLat, List<FenceItemModel> list){

        int count = list.size();

        Double point_x = 0.0;
        Double point_y = 0.0;
        Point2D.Double p1, p2; //neighbour bound vertices

        List<Point2D.Double> pointList = new ArrayList<>();
        for (FenceItemModel li : list) {

            point_x = Double.parseDouble(li.getLng());
            point_y = Double.parseDouble(li.getLat());
            Point2D.Double point1 = new Point2D.Double(point_x, point_y);
            pointList.add(point1);

        }
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差

        Point2D.Double p = new Point2D.Double(Double.parseDouble(pointLng), Double.parseDouble(pointLat));  //当前点

        p1 = pointList.get(0);//left vertex
        for(int i = 1; i <= count; ++i){//check all rays
            if(p.equals(p1)){
                return boundOrVertex;//p is an vertex
            }

            p2 = pointList.get(i % count);//right vertex
            if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){//ray is crossing over by the algorithm (common part of)
                if(p.y <= Math.max(p1.y, p2.y)){//x is before of ray
                    if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if(p1.y == p2.y){//ray is vertical
                        if(p1.y == p.y){//overlies on a vertical ray
                            return boundOrVertex;
                        }else{//before ray
                            ++intersectCount;
                        }
                    }else{//cross point on the left side
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y
                        if(Math.abs(p.y - xinters) < precision){//overlies on a ray
                            return boundOrVertex;
                        }

                        if(p.y < xinters){//before ray
                            ++intersectCount;
                        }
                    }
                }
            }else{//special case when ray is crossing through the vertex
                if(p.x == p2.x && p.y <= p2.y){//p crossing over p2
                    Point2D.Double p3 = pointList.get((i+1) % count); //next vertex
                    if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    }else{
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        if(intersectCount % 2 == 0){//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }
    }
}
