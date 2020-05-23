package au.edu.utas.sddhewa.assignment.ui.home;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import au.edu.utas.sddhewa.assignment.adapter.CurrentRaffleAdapter;
import au.edu.utas.sddhewa.assignment.adapter.PastRaffleAdapter;
import au.edu.utas.sddhewa.assignment.modal.Raffle;

public class PageViewModel extends ViewModel {

    private CurrentRaffleAdapter currentRaffles;

    private PastRaffleAdapter pastRaffles;

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    private LiveData<ArrayAdapter<Raffle>> liveRaffleData = Transformations.map(
            mIndex, new Function<Integer, ArrayAdapter<Raffle>>() {
        @Override
        public ArrayAdapter<Raffle> apply(Integer input) {
            switch (input) {
                case 0:
                    return currentRaffles;

                case 1:
                    return pastRaffles;

                default:
                    return null;
            }
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public void setCurrentRaffles (Context context, int resource, List<Raffle> objects, int tabId) {
        currentRaffles = new CurrentRaffleAdapter(context, resource, objects, tabId);
    }

    public CurrentRaffleAdapter getCurrentRafflesAdapter() {
        return currentRaffles;
    }

    public void setPastRaffles (Context context, int resource, List<Raffle> objects, int tabId) {
        pastRaffles = new PastRaffleAdapter(context, resource, objects);
    }

    public LiveData<ArrayAdapter<Raffle>> getRaffleList() {
        return liveRaffleData;
    }
}
