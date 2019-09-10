package com.example.a12306.bean;

import java.util.List;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * date   : 2019/9/814:01
 * desc   :
 * version: 1.0
 */
public class TicketNew {


    /**
     * id : 20193Yq
     * passengerList : [{"id":"37010519880414805X","name":"陈伟飞","idType":"身份证","tel":"13912341200","type":"成人","seat":{"seatName":"软卧","seatNum":42,"seatPrice":204,"seatNo":"4车55号"}},{"id":"310101199204163983","name":"哎米米","idType":"身份证","tel":"13912341234","type":"学生","seat":{"seatName":"软卧","seatNum":41,"seatPrice":204,"seatNo":"4车56号"}}]
     * train : {"trainNo":"G108","fromStationName":"沈阳","toStationName":"大连","startTime":"04:47","arriveTime":"14:46","dayDifference":0,"durationTime":"9小时59分","startTrainDate":"2019-9-9","seats":{"软卧":{"seatName":"软卧","seatNum":40,"seatPrice":204},"特等座":{"seatName":"特等座","seatNum":48,"seatPrice":288},"二等座":{"seatName":"二等座","seatNum":29,"seatPrice":249}}}
     * status : 0
     * orderTime : Sep 8, 2019 1:57:16 PM
     * orderPrice : 306.0
     */

    private String id;
    private TrainBean train;
    private String status;
    private String orderTime;
    private String orderPrice;
    private List<PassengerListBean> passengerList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TrainBean getTrain() {
        return train;
    }

    public void setTrain(TrainBean train) {
        this.train = train;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public List<PassengerListBean> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<PassengerListBean> passengerList) {
        this.passengerList = passengerList;
    }

    public static class TrainBean {
        /**
         * trainNo : G108
         * fromStationName : 沈阳
         * toStationName : 大连
         * startTime : 04:47
         * arriveTime : 14:46
         * dayDifference : 0
         * durationTime : 9小时59分
         * startTrainDate : 2019-9-9
         * seats : {"软卧":{"seatName":"软卧","seatNum":40,"seatPrice":204},"特等座":{"seatName":"特等座","seatNum":48,"seatPrice":288},"二等座":{"seatName":"二等座","seatNum":29,"seatPrice":249}}
         */

        private String trainNo;
        private String fromStationName;
        private String toStationName;
        private String startTime;
        private String arriveTime;
        private String dayDifference;
        private String durationTime;
        private String startTrainDate;
        private List<SeatsBean> seats;

        public List<SeatsBean> getSeats() {
            return seats;
        }

        public void setSeats(List<SeatsBean> seats) {
            this.seats = seats;
        }
        //        private SeatsBean seats;

        public String getTrainNo() {
            return trainNo;
        }

        public void setTrainNo(String trainNo) {
            this.trainNo = trainNo;
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

        public String getDayDifference() {
            return dayDifference;
        }

        public void setDayDifference(String dayDifference) {
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


        public static class SeatsBean {
            /**
             * 软卧 : {"seatName":"软卧","seatNum":40,"seatPrice":204}
             * 特等座 : {"seatName":"特等座","seatNum":48,"seatPrice":288}
             * 二等座 : {"seatName":"二等座","seatNum":29,"seatPrice":249}
             */
            private String seatName;
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

    public static class PassengerListBean {
        /**
         * id : 37010519880414805X
         * name : 陈伟飞
         * idType : 身份证
         * tel : 13912341200
         * type : 成人
         * seat : {"seatName":"软卧","seatNum":42,"seatPrice":204,"seatNo":"4车55号"}
         */

        private String id;
        private String name;
        private String idType;
        private String tel;
        private String type;
        private SeatBean seat;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public SeatBean getSeat() {
            return seat;
        }

        public void setSeat(SeatBean seat) {
            this.seat = seat;
        }

        public static class SeatBean {
            /**
             * seatName : 软卧
             * seatNum : 42
             * seatPrice : 204.0
             * seatNo : 4车55号
             */

            private String seatName;
            private String seatNum;
            private String seatPrice;
            private String seatNo;

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

            public String getSeatNo() {
                return seatNo;
            }

            public void setSeatNo(String seatNo) {
                this.seatNo = seatNo;
            }
        }
    }
}