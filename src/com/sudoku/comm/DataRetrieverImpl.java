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

        List<List<Integer>> resultMatrix = new ArrayList<>();
        Cell[][] matrix = grid.getGrid();
        for (byte i = 0; i < matrix.length; i++) {
          resultMatrix.add(i, new ArrayList<Integer>());
          for (byte j = 0; j < matrix[0].length; j++) {
            if (matrix[i][j] instanceof FixedCell) {
              resultMatrix.get(i).add(j,
                  (int) ((FixedCell) matrix[i][j]).getValue());
            } else {
              resultMatrix.get(i).add(j, null);
            }
          }
        }

        Grid toAddGrid = new Grid();
        toAddGrid.setCreateDate(grid.getCreateDate().toString());
        toAddGrid.setUpdateDate(grid.getUpdateDate().toString());
        toAddGrid.setId(grid.getId().toString());
        toAddGrid.setTitle(grid.getTitle());
        toAddGrid.setDescription(grid.getDescription());
        toAddGrid.setDifficulty(grid.getDifficulty());
        toAddGrid.setCreateUser(
              com.sudoku.data.model.User.buildAvroUser(grid.getCreateUser()));
        toAddGrid.setComments(comments);
        toAddGrid.setTags(tags);
        toAddGrid.setMatrix(resultMatrix);

        grids.add(toAddGrid);

//        grids.add(Grid.newBuilder()
//            .setCreateDate(toAddGrid.getCreateDate().toString())
//            .setUpdateDate(toAddGrid.getUpdateDate().toString())
//            .setId(toAddGrid.getId().toString())
//            .setTitle(toAddGrid.getTitle())
//            .setDescription(toAddGrid.getDescription())
//            .setDifficulty(toAddGrid.getDifficulty())
//            .setCreateUser(
//                com.sudoku.data.model.User.buildAvroUser(toAddGrid.getCreateUser()))
//            .setComments(comments)
//            .setTags(tags)
//            .setMatrix(resultMatrix)
//            .build());
        grids.add(toAddGrid);
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
