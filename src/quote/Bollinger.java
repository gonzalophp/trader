/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quote;

/**
 *
 * @author fiber
 */
public class Bollinger {
    private Double MA;
    private Double deviation;

    public Double getMA() {
        return MA;
    }

    public void setMA(Double MA) {
        this.MA = MA;
    }

    public Double getDeviation() {
        return deviation;
    }

    public void setDeviation(Double deviation) {
        this.deviation = deviation;
    }
    
    
    public Double getLowerBand(Double factor){
        return MA-factor*deviation;
    }
    
    public Double getUpperBand(Double factor){
        return MA+factor*deviation;
    }
}
