package xyz.kumaraswamy.lin2;

import xyz.kumaraswamy.lin2.structs.Result;

import java.util.List;

public interface ResultCallback {
  void onResult(List<Result> results);
}
