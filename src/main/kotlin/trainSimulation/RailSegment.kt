package trainSimulation

class RailSegment(val railID: Int){
    var capacity: Int = 0
    var delay: Boolean = false

    fun addTrain(){
        if(capacity < 3) {
            capacity++
        }else{
            delay = true
        }
    }
}