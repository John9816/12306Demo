package com.example.a12306.bean;

import java.util.List;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * date   : 2019/9/59:50
 * desc   :
 * version: 1.0
 */
public class TicketBean {
    @Override
    public String toString() {
        return "TicketBean{" +
                "trainNo='" + trainNo + '\'' +
                ", startStationName='" + startStationName + '\'' +
                ", endStationName='" + endStationName + '\'' +
                ", fromStationName='" + fromStationName + '\'' +
                ", toStationName='" + toStationName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                ", dayDifference=" + dayDifference +
                ", durationTime='" + durationTime + '\'' +
                ", startTrainDate='" + startTrainDate + '\'' +
                ", seats=" + seats +
                '}';
    }

    /**
     * trainNo : G108
     * startStationName : 北京
     * endStationName : 大连
     * fromStationName : 北京
     * toStationName : 大连
     * startTime : 04:47
     * arriveTime : 14:46
     * dayDifference : 0
     * durationTime : 9小时59分
     * startTrainDate : 2019-9-5
     * seats : {"商务座":{"seatName":"商务座","seatNum":2,"seatPrice":319},"高级软卧":{"seatName":"高级软卧","seatNum":17,"seatPrice":232},"一等座":{"seatName":"一等座","seatNum":19,"seatPrice":263}}
     */

    private String trainNo;
    private String startStationName;
    private String endStationName;
    private String fromStationName;
    private String toStationName;
    private String startTime;
    private String arriveTime;
    private int dayDifference;
    private String durationTime;
    private String startTrainDate;
    private List<SeatsBean> seats;

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getDayDifference() {
        return dayDifference;
    }

    public void setDayDifference(int dayDifference) {
        this.dayDifference = dayDifference;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getStartTrainDate() {
        return startTrainDate;
    }

    public void setStartTrainDate(String startTrainDate) {
        this.startTrainDate = startTrainDate;
    }

    public List<SeatsBean> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatsBean> seats) {
        this.seats = seats;
    }

    public static class SeatsBean {
        /**
         * 商务座 : {"seatName":"商务座","seatNum":2,"seatPrice":319}
         * 高级软卧 : {"seatName":"高级软卧","seatNum":17,"seatPrice":232}
         * 一等座 : {"seatName":"一等座","seatNum":19,"seatPrice":263}
         */
        private String seatName;

        @Override
        public String toString() {
            return "SeatsBean{" +
                    "seatName='" + seatName + '\'' +
                    ", seatNum='" + seatNum + '\'' +
                    ", seatPrice='" + seatPrice + '\'' +
                    '}';
        }

        private String seatNum;
        private String seatPrice;

        public String getSeatName() {
            return seatName;
        }

        public void setSeatName(String seatName) {
            this.seatName = seatName;
        }

        public String getSeatNum() {
            return seatNum;
        }

        public void setSeatNum(String seatNum) {
            this.seatNum = seatNum;
        }

        public String getSeatPrice() {
            return seatPrice;
        }

        public void setSeatPrice(String seatPrice) {
            this.seatPrice = seatPrice;
        }
    }
}
