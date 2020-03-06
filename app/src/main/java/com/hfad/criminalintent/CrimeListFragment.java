package com.hfad.criminalintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment
{
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_crime_list,container,false );

        mCrimeRecyclerView = view.findViewById ( R.id.crime_recycler_view );
        mCrimeRecyclerView.setLayoutManager ( new LinearLayoutManager ( getActivity () ) );

        updateUI ();

        return view;
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;
        private ImageView mSolvedImageView;

        public CrimeHolder(LayoutInflater inflater,ViewGroup parent)
        {
            super(inflater.inflate ( R.layout.list_item_crime,parent,false ));

            itemView.setOnClickListener ( this );

            mTitleTextView = itemView.findViewById ( R.id.crime_title );
            mDateTextView = itemView.findViewById ( R.id.crime_date );
            mSolvedImageView = itemView.findViewById ( R.id.crime_solved );
        }

        public void bind(Crime crime)
        {
            mCrime = crime;
            mTitleTextView.setText ( mCrime.getTitle () );
            mDateTextView.setText ( mCrime.getDate ().toString () );
            mSolvedImageView.setVisibility ( crime.isSolved () ? View.VISIBLE : View.GONE );
        }

        @Override
        public void onClick(View view) {
            Toast.makeText ( getActivity (),mCrime.getTitle () + "Clicked",Toast.LENGTH_SHORT).show ();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>
    {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> list)
        {
            mCrimes = list;
        }

        @Override
        public int getItemCount() {
            return mCrimes.size ();
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from ( getActivity () );

            return new CrimeHolder ( layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder crimeHolder, int i) {

            Crime crime = mCrimes.get ( i );
            crimeHolder.bind ( crime );
        }
    }

    private void updateUI()
    {
        CrimeLab crimeLab = CrimeLab.get ( getActivity () );
        List<Crime> crimes = crimeLab.getCrimes ();

        mAdapter = new CrimeAdapter (crimes);
        mCrimeRecyclerView.setAdapter ( mAdapter );
    }
}
