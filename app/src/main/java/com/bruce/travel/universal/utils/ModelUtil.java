package com.bruce.travel.universal.utils;

import com.bruce.travel.R;
import com.bruce.travel.finds.model.TravelNotesInfo;
import com.bruce.travel.message.model.DestinationDetailInfo;
import com.bruce.travel.message.model.DestinationInfo;
import com.bruce.travel.travels.been.ChannelBean;
import com.bruce.travel.travels.been.FilterBean;
import com.bruce.travel.travels.been.FilterTwoBean;
import com.bruce.travel.travels.been.TravelsBean;
import com.bruce.travel.travels.been.TravelsEntityComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 好吧，让你找到了，这是假的数据源
 *
 * Created by sunfusheng on 16/4/22.
 */
public class ModelUtil {

    public static final String type_scenery = "风景";
    public static final String type_building = "建筑";
    public static final String type_animal = "动物";
    public static final String type_plant = "植物";

    // 广告数据
    public static List<String> getAdData() {
        List<String> adList = new ArrayList<>();
        adList.add("http://img0.imgtn.bdimg.com/it/u=1270781761,1881354959&fm=21&gp=0.jpg");
        adList.add("http://img0.imgtn.bdimg.com/it/u=2138116966,3662367390&fm=21&gp=0.jpg");
        adList.add("http://img0.imgtn.bdimg.com/it/u=1296117362,655885600&fm=21&gp=0.jpg");
//        adList.add("http://img.mp.itc.cn/upload/20160814/5b8d5cabb8a3417c85f9a1f7ef421d03_th.jpg");
//        adList.add("http://jiangsu.china.com.cn/uploadfile/2016/0815/1471251091317279.jpg");
//        adList.add("http://img.xianzhaiwang.cn/d/file/xinwen/roll/ab2b1d1135bc912163c80e5f94c0930b.jpg");
        adList.add("http://img2.ph.126.net/uA75ADvzg-TnPnOsBONenw==/6608534374399192856.jpg");
        adList.add("http://upload.17u.com/uploadfile/2015/01/09/2/201501091014115097620.jpg");
        adList.add("http://m.tuniucdn.com/fb2/t1/G1/M00/0B/32/Cii9EVZFhHWITxtjAAM2lV-HknoAAAMGgPmgKoAAzat612_w800_h400_c1_t0.jpg");
        return adList;
    }

    // 频道数据
    public static List<ChannelBean> getChannelData() {
        List<ChannelBean> channelList = new ArrayList<>();
        channelList.add(new ChannelBean("中国", "天安门", "http://img2.imgtn.bdimg.com/it/u=2850936076,2080165544&fm=206&gp=0.jpg"));
        channelList.add(new ChannelBean("美国", "白宫", "http://img3.imgtn.bdimg.com/it/u=524208507,12616758&fm=206&gp=0.jpg"));
        channelList.add(new ChannelBean("英国", "伦敦塔桥", "http://img3.imgtn.bdimg.com/it/u=698582197,4250615262&fm=206&gp=0.jpg"));
//        channelList.add(new ChannelBean("德国", "城堡", "http://img5.imgtn.bdimg.com/it/u=1467751238,3257336851&fm=11&gp=0.jpg"));
//        channelList.add(new ChannelBean("西班牙", "巴塞罗那", "http://img5.imgtn.bdimg.com/it/u=3191365283,111438732&fm=21&gp=0.jpg"));
//        channelList.add(new ChannelBean("意大利", "比萨斜塔", "http://img5.imgtn.bdimg.com/it/u=482494496,1350922497&fm=206&gp=0.jpg"));
        return channelList;
    }
//
//    // 运营数据
//    public static List<OperationEntity> getOperationData() {
//        List<OperationEntity> operationList = new ArrayList<>();
//        operationList.add(new OperationEntity("度假游", "度假的天堂", "http://img2.imgtn.bdimg.com/it/u=4081165325,36916497&fm=21&gp=0.jpg"));
//        operationList.add(new OperationEntity("蜜月游", "浪漫的港湾", "http://img4.imgtn.bdimg.com/it/u=4141168524,78676102&fm=21&gp=0.jpg"));
//        return operationList;
//    }
//
    // ListView数据
    public static List<TravelsBean> getTravelingData() {
        List<TravelsBean> travelingList = new ArrayList<>();
        travelingList.add(new TravelsBean(  "中国", "2016-12-06", "http://img5.imgtn.bdimg.com/it/u=2769726205,1778838650&fm=21&gp=0.jpg"));
        travelingList.add(new TravelsBean( "西班牙", "2016-12-15", "http://img1.imgtn.bdimg.com/it/u=1832737924,144748431&fm=21&gp=0.jpg"));
        travelingList.add(new TravelsBean( "意大利", "2016-12-06", "http://img5.imgtn.bdimg.com/it/u=2091366266,1524114981&fm=21&gp=0.jpg"));
        travelingList.add(new TravelsBean( "荷兰", "2016-06-06", "http://img4.imgtn.bdimg.com/it/u=3673198446,2175517238&fm=206&gp=0.jpg"));
        travelingList.add(new TravelsBean( "加拿大", "2016-03-06", "http://img4.imgtn.bdimg.com/it/u=3052089044,3887933556&fm=21&gp=0.jpg"));
        travelingList.add(new TravelsBean("韩国", "2015-10-06", "http://img2.imgtn.bdimg.com/it/u=140083303,1086773509&fm=21&gp=0.jpg"));
        travelingList.add(new TravelsBean("英国", "2015-10-17", "http://img5.imgtn.bdimg.com/it/u=1424970962,1243597989&fm=21&gp=0.jpg"));
        travelingList.add(new TravelsBean("泰国", "2016-11-26", "http://img4.imgtn.bdimg.com/it/u=1387833256,3665925904&fm=21&gp=0.jpg"));
        travelingList.add(new TravelsBean("新加坡", "2016-06-12", "http://img1.imgtn.bdimg.com/it/u=3808801622,1608105009&fm=21&gp=0.jpg")); return travelingList;
    }

    public static List<TravelNotesInfo> getTravelNotesData() {
        List<TravelNotesInfo> travelNotesList = new ArrayList<>();
        travelNotesList.add(new TravelNotesInfo("沿着大草原前进，舞动青春","青海湖", R.drawable.user_head_icon,"柠檬","16-06-18",R.drawable.travel_note_bg1));
        travelNotesList.add(new TravelNotesInfo("邂逅美景之呼伦贝尔大草原","呼伦贝尔", R.drawable.user_head_icon,"可乐","16-06-18",R.drawable.travel_detail_bg2));
        travelNotesList.add(new TravelNotesInfo("蓝天世界有你有我","希腊", R.drawable.user_head_icon,"小九","16-06-18",R.drawable.travel_detail_bg4));
        travelNotesList.add(new TravelNotesInfo("2016我们在厦门","厦门", R.drawable.user_head_icon,"兰","16-06-18",R.drawable.travel_note_bg1));
        travelNotesList.add(new TravelNotesInfo("邂逅美景之呼伦贝尔大草原","呼伦贝尔", R.drawable.user_head_icon,"可乐","16-06-18",R.drawable.travel_detail_bg2));
        travelNotesList.add(new TravelNotesInfo("蓝天世界有你有我","希腊", R.drawable.user_head_icon,"缘","16-06-18",R.drawable.travel_note_bg1));
        travelNotesList.add(new TravelNotesInfo("邂逅美景之呼伦贝尔大草原","呼伦贝尔", R.drawable.user_head_icon,"兰","16-06-18",R.drawable.travel_note_bg1));
        return travelNotesList;
    }

    public static List<DestinationInfo> getDestinationData() {
        List<DestinationInfo> dstInfo = new ArrayList<>();
        dstInfo.add(new DestinationInfo("热门"));
        dstInfo.add(new DestinationInfo("国内"));
        dstInfo.add(new DestinationInfo("韩国"));
        dstInfo.add(new DestinationInfo("马尔代夫"));
        dstInfo.add(new DestinationInfo("欧洲"));
        dstInfo.add(new DestinationInfo("泰国"));
        dstInfo.add(new DestinationInfo("日本"));
        dstInfo.add(new DestinationInfo("东南亚"));
        dstInfo.add(new DestinationInfo("美洲"));
        dstInfo.add(new DestinationInfo("港澳"));
        dstInfo.add(new DestinationInfo("台湾"));
        return dstInfo;
    }

//    public static Map<Integer, List<DestinationDetailInfo>> getDestinationDetailData() {
//        Map<Integer, List<DestinationDetailInfo>> dstInfo = new HashMap<>();
//        List<DestinationDetailInfo> dstHotInfo = new ArrayList<>();
//        dstHotInfo.add(new DestinationDetailInfo("remen111"));
//        dstHotInfo.add(new DestinationDetailInfo("remen222"));
//        dstInfo.put(0, dstHotInfo);
//
//        return dstInfo;
//    }

    public static List<DestinationDetailInfo> getDestinationDetailData() {
        List<DestinationDetailInfo> dstInfo = new ArrayList<>();
        dstInfo.add(new DestinationDetailInfo("热门"));
        dstInfo.add(new DestinationDetailInfo("国内"));
        dstInfo.add(new DestinationDetailInfo("韩国"));
        dstInfo.add(new DestinationDetailInfo("马尔代夫"));
        dstInfo.add(new DestinationDetailInfo("欧洲"));
        return dstInfo;
    }

    public static List<DestinationDetailInfo> getDestinationDetailData1() {
        List<DestinationDetailInfo> dstInfo = new ArrayList<>();
        dstInfo.add(new DestinationDetailInfo("热门1"));
        dstInfo.add(new DestinationDetailInfo("国内1"));
        dstInfo.add(new DestinationDetailInfo("韩国1"));
        dstInfo.add(new DestinationDetailInfo("马尔代夫1"));
        dstInfo.add(new DestinationDetailInfo("欧洲1"));
        return dstInfo;
    }

    public static List<DestinationDetailInfo> getDestinationDetailData2() {
        List<DestinationDetailInfo> dstInfo = new ArrayList<>();
        dstInfo.add(new DestinationDetailInfo("热门2"));
        dstInfo.add(new DestinationDetailInfo("国内2"));
        dstInfo.add(new DestinationDetailInfo("韩国2"));
        dstInfo.add(new DestinationDetailInfo("马尔代夫2"));
        dstInfo.add(new DestinationDetailInfo("欧洲2"));
        return dstInfo;
    }



    // 分类数据
    public static List<FilterTwoBean> getCategoryData() {
        List<FilterTwoBean> list = new ArrayList<>();
        list.add(new FilterTwoBean(type_scenery, getFilterData()));
        list.add(new FilterTwoBean(type_building, getFilterData()));
        list.add(new FilterTwoBean(type_animal, getFilterData()));
        list.add(new FilterTwoBean(type_plant, getFilterData()));
        return list;
    }

    // 排序数据
    public static List<FilterBean> getSortData() {
        List<FilterBean> list = new ArrayList<>();
        list.add(new FilterBean("排序从高到低", "1"));
        list.add(new FilterBean("排序从低到高", "2"));
        return list;
    }

    // 筛选数据
    public static List<FilterBean> getFilterData() {
        List<FilterBean> list = new ArrayList<>();
        list.add(new FilterBean("中国", "1"));
        list.add(new FilterBean("美国", "2"));
        list.add(new FilterBean("英国", "3"));
        list.add(new FilterBean("德国", "4"));
        list.add(new FilterBean("西班牙", "5"));
        list.add(new FilterBean("意大利", "6"));
        return list;
    }

    // ListView分类数据
    public static List<TravelsBean> getCategoryTravelingData(FilterTwoBean entity) {
        List<TravelsBean> list = getTravelingData();
        List<TravelsBean> travelingList = new ArrayList<>();
        int size = list.size();
//        for (int i=0; i<size; i++) {
//            if (list.get(i).getType().equals(entity.getType()) &&
//                    list.get(i).getFrom().equals(entity.getSelectedFilterEntity().getKey())) {
//                travelingList.add(list.get(i));
//            }
//        }
        return travelingList;
    }

    // ListView排序数据
    public static List<TravelsBean> getSortTravelingData(FilterBean entity) {
        List<TravelsBean> list = getTravelingData();
        Comparator<TravelsBean> ascComparator = new TravelsEntityComparator();
        if (entity.getKey().equals("排序从高到低")) {
            Collections.sort(list);
        } else {
            Collections.sort(list, ascComparator);
        }
        return list;
    }

    // ListView筛选数据
    public static List<TravelsBean> getFilterTravelingData(FilterBean entity) {
        List<TravelsBean> list = getTravelingData();
        List<TravelsBean> travelingList = new ArrayList<>();
        int size = list.size();
//        for (int i=0; i<size; i++) {
//            if (list.get(i).getFrom().equals(entity.getKey())) {
//                travelingList.add(list.get(i));
//            }
//        }
        return travelingList;
    }

//    // 暂无数据
//    public static List<TravelsBean> getNoDataEntity(int height) {
//        List<TravelsBean> list = new ArrayList<>();
//        TravelsBean entity = new TravelsBean();
//        entity.setNoData(true);
//        entity.setHeight(height);
//        list.add(entity);
//        return list;
//    }

}
