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
import java.util.UUID;

/**
 * Implementation of the Avro-generated DataRetriever interface
 * @author Benjamin Fradet
 * @see DataRetriever
 */
public class DataRetrieverImpl implements DataRetriever {
  /**
   * Retrieves the list of available grids on the server
   * @return A list of Avro-generated grids
   * @throws AvroRemoteException
   */
  @Override
  public List<Grid> getGrids() throws AvroRemoteException {
    List<com.sudoku.data.model.Grid> availableGrids =
        GridManager.getInstance().getAvailableGrids();
    List<Grid> grids = new ArrayList<>();
    if (availableGrids != null) {
      for (com.sudoku.data.model.Grid grid : availableGrids) {
        List<Comment> comments = new ArrayList<>();
        for (com.sudoku.data.model.Comment comment : grid.getComments()) {
          comments.add(Comment.newBuilder()
              .setAuthor(
                  com.sudoku.data.model.User.buildAvroUser(comment.getAuthor()))
              .setComment(comment.getComment())
              .setGrade(comment.getGrade())
              .build());
        }

        List<String> tags = new ArrayList<>();
        for (com.sudoku.data.model.Tag tag : grid.getTags()) {
          tags.add(tag.getName());
        }

        Cell[][] matrix = grid.getGrid();
        List<List<Integer>> resultMatrix = new ArrayList<>();
        for (byte i = 0; i < matrix.length; i++) {
          List<Integer> list = new ArrayList<>();
          resultMatrix.add(i, list);
          for (byte j = 0; j < matrix[0].length; j++) {
            if (matrix[i][j] instanceof FixedCell) {
              list.add(j, (int) ((FixedCell) matrix[i][j]).getValue());
            } else {
              list.add(j, -1);
            }
          }
        }

        grids.add(Grid.newBuilder()
            .setCreateDate(grid.getCreateDate().toString())
            .setUpdateDate(grid.getUpdateDate().toString())
            .setId(grid.getId().toString())
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
    }
    return grids;
  }

  /**
   * Retrieves the profile of the user on the server
   * @return An Avro-generated user object
   * @throws AvroRemoteException
   */
  @Override
  public User getProfile() throws AvroRemoteException {
    com.sudoku.data.model.User currentUser =
        UserManager.getInstance().getLoggedUser();
    return com.sudoku.data.model.User.buildAvroUser(currentUser);
  }

  /**
   * Add a comment to a specific grid
   * @param comment the comment to be added
   * @param gridUuid the uuid specific to the grid to be commented
   * @return nothing
   * @throws AvroRemoteException
   */
  @Override
  public Void commentGrid(Comment comment, String gridUuid)
      throws AvroRemoteException {
    com.sudoku.data.model.Grid grid =
        GridManager.getInstance().getGridById(UUID.fromString(gridUuid));
    if (grid != null) {
      grid.addComment(com.sudoku.data.model.Comment
          .buildFromAvroComment(comment));
    }
    return null;
  }
}
