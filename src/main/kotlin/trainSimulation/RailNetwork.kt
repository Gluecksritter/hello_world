package trainSimulation

class RailNetwork(var railSegments: List<RailSegment>){

    fun delayFunction(listTrains: List<Train>, listSegments: List<RailSegment>): List<RailSegment>{
        for(train in listTrains){
            var num: Int = train.schedule.railSegment
            for(segment in listSegments){
                if(segment.railID == num){
                    segment.addTrain()
                }
            }
        }
        return listSegments
    }

    fun applyDelay(listTrains: List<Train>, listSegments: List<RailSegment>): List<Train>{
        for(train in listTrains){
            for(segment in listSegments){
                if(train.schedule.railSegment == segment.railID && segment.delay){
                    train.delayed = true
                }
            }
        }

        return listTrains
    }

}