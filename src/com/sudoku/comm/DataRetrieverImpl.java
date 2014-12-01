package com.sudoku.comm;

import com.sudoku.comm.generated.Comment;
import com.sudoku.comm.generated.DataRetriever;
import com.sudoku.comm.generated.Grid;
import com.sudoku.comm.generated.User;
import com.sudoku.data.manager.GridManager;
import com.sudoku.data.manager.UserManager;
import com.sudoku.data.model.Cell;
import com.sudoku.data.model.FixedCell;
import org.apache.avro.AvroRemoteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 26/11/14.
 */
public class DataRetrieverImpl implements DataRetriever {
  @Override
  public List<Grid> getGrids() throws AvroRemoteException {
    List<com.sudoku.data.model.Grid> availableGrids =
        GridManager.getInstance().getAvailableGrids();
    ArrayList<Grid> grids = new ArrayList<>();
    for (com.sudoku.data.model.Grid grid : availableGrids) {
      ArrayList<Comment> comments = new ArrayList<>();
      for (com.sudoku.data.model.Comment comment : grid.getComments()) {
        comments.add(Comment.newBuilder()
            .setAuthor(
                com.sudoku.data.model.User.buildAvroUser(comment.getAuthor()))
            .setComment(comment.getComment())
            .setGrade(comment.getGrade())
            .build());
      }

      ArrayList<String> tags = new ArrayList<>();
      for (com.sudoku.data.model.Tag tag : grid.getTags()) {
        tags.add(tag.getName());
      }

      List<List<Integer>> resultMatrix = new ArrayList<>();
      Cell[][] matrix = grid.getGrid();
      for (byte i = 0; i < matrix.length; i++) {
        resultMatrix.add(i, new ArrayList<Integer>());
        for (byte j = 0; j < matrix[0].length; j++) {
          if (matrix[i][j] instanceof FixedCell) {
            resultMatrix.get(i).add(j,
                (int)((FixedCell) matrix[i][j]).getValue());
          } else {
            resultMatrix.get(i).add(j, null);
          }
        }
      }

      grids.add(Grid.newBuilder()
          .setCreateDate(grid.getCreateDate().toString())
          .setUpdateDate(grid.getUpdateDate().toString())
          .setId(grid.getId())
          .setTitle(grid.getTitle())
          .setDescription(grid.getDescription())
          .setDifficulty(grid.getDifficulty())
          .setCreateUser(
              com.sudoku.data.model.User.buildAvroUser(grid.getCreateUser()))
          .setComments(comments)
          .setTags(tags)
          .setMatrix(resultMatrix)
          .build());
    }
    return grids;
  }

  @Override
  public User getProfile() throws AvroRemoteException {
    com.sudoku.data.model.User currentUser =
        UserManager.getInstance().getLoggedUser();
    return com.sudoku.data.model.User.buildAvroUser(currentUser);
  }
}
