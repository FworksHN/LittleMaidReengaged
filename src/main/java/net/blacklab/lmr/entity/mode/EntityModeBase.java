package net.blacklab.lmr.entity.mode;

import java.util.List;

import net.blacklab.lmr.client.renderer.entity.RenderLittleMaid;
import net.blacklab.lmr.entity.EntityLittleMaid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;

/**
 * LMM用独自AI処理に使用。
 * この継承クラスをAI処理として渡すことができる。
 * また、AI処理選択中は特定の関数を除いて選択中のクラスのみが処理される。
 * インスタンス化する事によりローカル変数を保持。
 */
public abstract class EntityModeBase {

	public final EntityLittleMaid owner;
	
	public boolean isAnytimeUpdate = false;

	/**
	 * 初期化
	 */
	public EntityModeBase(EntityLittleMaid pEntity) {
		owner = pEntity;
	}

	public int fpriority;

	/**
	 * 優先順位。
	 * 番号が若いほうが先に処理される。
	 * 下二桁が00のものはシステム予約。
	 */
	public abstract int priority();

	/**
	 * 起動時の初期化。
	 */
	public void init() {
	}

	/**
	 * Entity初期化時の実行部
	 */
	public void initEntity() {
	}

	/**
	 * モードの追加。
	 */
	public abstract void addEntityMode(EntityAITasks pDefaultMove, EntityAITasks pDefaultTargeting);

	/**
	 * 独自データ保存用。
	 */
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
	}
	/**
	 * 独自データ読込用。
	 */
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
	}

	/**
	 * renderSpecialの追加実装用。
	 */
	public void showSpecial(RenderLittleMaid prenderlittlemaid, double px, double py, double pz) {
	}

	/**
	 * サーバー側のみの毎時処理。
	 * AI処理の後の方に呼ばれる。
	 */
	public void updateAITick(int pMode) {
	}

	/**
	 * 毎時処理。
	 * 他の処理の前に呼ばれる
	 */
	public void onUpdate(int pMode) {
	}

	/**
	 * このへんの処理は若干時間かかっても良し。
	 * 他のアイテムを使用したい時。
	 * 補完処理に先んじて実行される、その代わり判定も全部自分持ち。
	 */
	public boolean preInteract(EntityPlayer pentityplayer, ItemStack pitemstack) {
		return false;
	}
	/**
	 * このへんの処理は若干時間かかっても良し。
	 * 他のアイテムを使用したい時。
	 */
	public boolean interact(EntityPlayer pentityplayer, ItemStack pitemstack) {
		return false;
	}

	/**
	 * 砂糖でモードチェンジした時。
	 */
	public boolean changeMode(EntityPlayer pentityplayer) {
		return false;
	}

	/**
	 * モードチェンジ時の設定処理の本体。
	 * こっちに処理を書かないとロード時におかしくなるかも？
	 */
	public boolean setMode(int pMode) {
		return false;
	}

	/**
	 * 使用アイテムの選択。
	 * 戻り値はスロット番号
	 */
	public int getNextEquipItem(int pMode) {
		// 未選択
		return -1;
	}
	
	/**
	 * アイテム回収可否の判定式。
	 * 拾いに行くアイテムの判定。
	 */
	public boolean checkItemStack(ItemStack pItemStack) {
		// 回収対象アイテムの設定なし
		return false;
	}

	/**
	 * 攻撃判定処理。
	 * 特殊な攻撃動作はここで実装。
	 */
	public boolean attackEntityAsMob(int pMode, Entity pEntity) {
		// 特殊攻撃の設定なし
		return false;
	}

	/**
	 * ブロックのチェック判定をするかどうか。
	 * 判定式のどちらを使うかをこれで選択。
	 */
	public boolean isSearchBlock() {
		return false;
	}

	/**
	 * isSearchBlock=falseのときに判定される。
	 */
	public boolean shouldBlock(int pMode) {
		return false;
	}

	/**
	 * 探し求めたブロックであるか。
	 * trueを返すと検索終了。
	 */
	public boolean checkBlock(int pMode, int px, int py, int pz) {
		return false;
	}

	/**
	 * 検索範囲に索敵対象がなかった。
	 */
	public boolean overlooksBlock(int pMode) {
		return false;
	}
//	@Deprecated
//	public TileEntity overlooksBlock(int pMode) {
//		return null;
//	}

	/**
	 * 限界距離を超えた時の処理
	 */
	public void farrangeBlock() {
		owner.getNavigator().clearPathEntity();
	}

	/**
	 * 有効射程距離を超えた時の処理
	 */
	public boolean outrangeBlock(int pMode, int pX, int pY, int pZ) {
		return owner.getNavigator().tryMoveToXYZ(pX, pY, pZ, 1.0F);
	}
	public boolean outrangeBlock(int pMode) {
		return outrangeBlock(pMode, owner.maidTile[0], owner.maidTile[1], owner.maidTile[2]);
	}

	/**
	 * 射程距離に入ったら実行される。
	 * 戻り値がtrueの時は終了せずに動作継続
	 */
	public boolean executeBlock(int pMode, int px, int py, int pz) {
		return false;
	}
	public boolean executeBlock(int pMode) {
		return executeBlock(pMode, owner.maidTile[0], owner.maidTile[1], owner.maidTile[2]);
	}

	/**
	 * AI実行時に呼ばれる。
	 */
	public void startBlock(int pMode) {
	}

	/**
	 * AI終了時に呼ばれる。
	 */
	public void resetBlock(int pMode) {
	}

	/**
	 * 継続判定を行う時に呼ばれる。
	 */
	public void updateBlock() {
	}
	
	/**
	 * ワープ時にEntityから呼ばれる．
	 */
	public void onWarp() {
	}

	/**
	 * 独自索敵処理の使用有無
	 */
	public boolean isSearchEntity() {
		return false;
	}

	/**
	 * 独自索敵処理
	 */
	public boolean checkEntity(int pMode, Entity pEntity) {
		return false;
	}

	/**
	 * 発光処理用
	 */
	public int colorMultiplier(float pLight, float pPartialTicks) {
		return 0;
	}
	
	/**
	 * 被ダメ時の処理１。
	 * 0以上を返すと処理を乗っ取る。
	 * 1:falseで元の処理を終了する。
	 * 2:trueで元の処理を終了する。
	 */
	public float attackEntityFrom(DamageSource par1DamageSource, float par2) {
		return 0;
	}
	/**
	 * 被ダメ時の処理２。
	 * trueを返すと処理を乗っ取る。
	 */
	public boolean damageEntity(int pMode, DamageSource par1DamageSource, float par2) {
		return false;
	}

	/**
	 * 自分が使っているTileならTrueを返す。
	 */
	public boolean isUsingTile(TileEntity pTile) {
		return false;
	}

	/**
	 * 持ってるTileを返す。
	 */
	public List<TileEntity> getTiles() {
		return null;
	}

	/**
	 * Returns the squared distance from master to start following.
	 */
	public double getDistanceSqToStartFollow() {
		return 25d;
	}
	
	/**
	 * Returns the squared distance from master to teleport.
	 */
	public double getLimitRangeSqOnFollow() {
		return 100d;
	}
	
	/**
	 * Returns the squared radius of the area on which freedom maids can act.
	 */
	public double getFreedomTrackingRange() {
		return 361d;
	}

	/**
	 * 攻撃後にターゲットを再設定させるかの指定。
	 * @param pTarget
	 * @return
	 */
	public boolean isChangeTartget(Entity pTarget) {
		return !owner.isBloodsuck();
	}

}
