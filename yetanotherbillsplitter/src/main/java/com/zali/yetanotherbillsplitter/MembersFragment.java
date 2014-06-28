package com.zali.yetanotherbillsplitter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zali.yetanotherbillsplitter.db.DbHelper;
import com.zali.yetanotherbillsplitter.db.Member;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the interface.
 */
public class MembersFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MembersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DbHelper db = new DbHelper(this.getActivity());

        // TODO: Change Adapter to display your content
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, db.getAllMembersList()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.members, container, false);

        final EditText text = (EditText) view.findViewById(R.id.newName);
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return addMember(container, text);
            }
        });

        return view;
    }

    protected boolean addMember(ViewGroup container, EditText text) {

        // validate and save user
        String memberName = text.getText().toString();
        if (memberName == null || memberName.length() < 3) {
            Toast.makeText(container.getContext(),
                    "Name can't be shorter than 3 characters",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();

        // write into database
        DbHelper db = new DbHelper(getActivity());
        Member m = new Member(memberName);
        m.setId(db.addMember(m));

        // update list
        adapter.add(m);

        text.setText("");

        return true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DbHelper db = new DbHelper(getActivity());

        // Remove item
        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();
        Member m = (Member) adapter.getItem(position);
        db.deleteMember(m.getId());
        adapter.remove(m);

        /*
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(db.getAllMembersList().get(position).getId());
        }
        */
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(long id);
    }
}